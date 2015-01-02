(ns cidr.core
  (:require [clojure.string :as str]))

(defn- expt
  [x pow]
  (apply * (repeat pow x)))

(defn- shft
  [num to]
  (bit-shift-right (bit-and num (bit-shift-left 0xff to)) to))

(defn- ip->num
  [[a b c d]]
  (+ (* a (expt 256 3))
     (* b (expt 256 2))
     (* c 256)
     d))

(defn- num->ip
  [num]
  [(shft num 24)
   (shft num 16)
   (shft num 8)
   (bit-and num 0xFF)])

(defn- cidr->ips
  [a b c d len]
  (let [nw (ip->num [a b c d])
        mask (bit-shift-left -1 (- 32 len))
        low (bit-and nw mask)
        high (+ low (bit-not mask))]
    [low high]))

(defn- in-cidr-range?
  "In range? [cider] [ip]."
  [[a b c d len] [w x y z]]
  (let [[low high] (cidr->ips a b c d len)
        val (ip->num [w x y z])]
    (and (>= val low)
         (<= val high))))

(defn- str->ip
  [s]
  (->> (str/split s #"[.]")
       (mapv #(Integer/parseInt %))))

(defn- str->cidr
  [s]
  (let [parts (str/split s #"/")
        quads (str/split (first parts) #"[.]")
        quads (take 4 (apply conj quads ["0" "0" "0" "0"]))]
    (->> (conj (vec quads) (second parts))
         (mapv #(Integer/parseInt %)))))

(defn- ip->str
  [ip]
  (str/join "." ip))

(defn- num->str
  [num]
  (ip->str (num->ip num)))

(defn ip-range
  [cidr-str]
  (let [cidr (str->cidr cidr-str)
        min-and-max (apply cidr->ips cidr)]
    (mapv num->str min-and-max)))

(defn ips-in-range
  [cidr-str]
  (let [cidr (str->cidr cidr-str)
        [min max] (apply cidr->ips cidr)
        nums (range min (inc max))]
    (lazy-seq (map num->str nums))))

(defn in-range?
  [ip-str cidr-str]
  (let [ip (str->ip ip-str)
        cidr (str->cidr cidr-str)]
    (in-cidr-range? cidr ip)))

(defn completely-in-range?
  "Will return true if `cidr-str-a` is contained completely in `cidr-str-b`."
  [cidr-str-a cidr-str-b]
  (let [[min-a max-a] (apply cidr->ips (str->cidr cidr-str-a))
        [min-b max-b] (apply cidr->ips (str->cidr cidr-str-b))]
    (and (>= min-a min-b)
         (<= max-a max-b))))

(defn overlap?
  "Will return true if `cidr-str-a` overlaps with `cidr-str-b`."
  [cidr-str-a cidr-str-b]
  (let [[min-a max-a] (apply cidr->ips (str->cidr cidr-str-a))
        [min-b max-b] (apply cidr->ips (str->cidr cidr-str-b))]
    (or (>= min-a min-b)
        (<= max-b max-a))))
