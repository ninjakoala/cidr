(ns cidr.core-test
  (:require [cidr.core :refer :all]
            [midje.sweet :refer :all]))

(fact "that an IP address which is in the range is true"
      (in-range? "10.0.0.1" "10.0.0.1/32") => true)

(fact "that an IP address which isn't above the range is false"
      (in-range? "9.255.255.255" "10.0.0.0/32") => false)

(fact "that an IP address which isn't above the range is false"
      (in-range? "10.0.0.1" "10.0.0.0/32") => false)

(fact "that getting the IP range for a CIDR gives the min and max addresses"
      (ip-range "10.0.0.0/0") => ["0.0.0.0" "255.255.255.255"])

(fact "that getting the IP addresses in CIDR gives those addresses back in string form"
      (ips-in-range "10.0.0.0/31") => ["10.0.0.0" "10.0.0.1"])

(fact "that working out whether a range is completely contained within another works when they do overlap"
      (completely-in-range? "10.0.0.1/32" "10.0.0.0/31") => true)

(fact "that working out whether a range is completely contained within another works when they only partially overlap at the low-end"
      (completely-in-range? "10.0.0.255/30" "10.0.0.255/32") => false)

(fact "that working out whether a range is completely contained within another works when they only partially overlap at the high-end"
      (completely-in-range? "10.0.0.2/30" "10.0.0.3/32") => false)

(fact "that working out whether two ranges overlap returns false when they don't overlap at all"
      (overlap? "9.0.0.0/8" "10.0.0.0/8") => false)

(fact "that working out whether two ranges overlap returns false when they don't overlap at all"
      (overlap? "10.0.0.255/30" "10.0.0.255/32") => true)

(fact "that working out whether two ranges overlap returns false when they don't overlap at all"
      (overlap? "10.0.0.2/30" "10.0.0.3/32") => true)
