(ns cidr.core-test
  (:require [cidr.core :refer :all]
            [clojure.test :refer [deftest is testing]]))

(deftest in-range?-test
  (testing "that an IP address which is in the range is true"
    (is (in-range? "10.0.0.1" "10.0.0.1/32")))

  (testing "that an IP address which isn't above the range is false"
    (is (not (in-range? "9.255.255.255" "10.0.0.0/32"))))

  (testing "that an IP address which isn't above the range is false"
    (is (not (in-range? "10.0.0.1" "10.0.0.0/32"))))

  (testing "that getting the IP range for a CIDR gives the min and max addresses"
    (is (= ["0.0.0.0" "255.255.255.255"]
           (ip-range "10.0.0.0/0")))))

(deftest ips-in-range-test
  (testing "that getting the IP addresses in CIDR gives those addresses back in string form"
    (is (= ["10.0.0.0" "10.0.0.1"]
           (ips-in-range "10.0.0.0/31")))))

(deftest completely-in-range?-test
  (testing "that working out whether a range is completely contained within another works when they do overlap"
    (is (completely-in-range? "10.0.0.1/32" "10.0.0.0/31")))

  (testing "that working out whether a range is completely contained within another works when they only partially overlap at the low-end"
    (is (not (completely-in-range? "10.0.0.255/30" "10.0.0.255/32"))))

  (testing "that working out whether a range is completely contained within another works when they only partially overlap at the high-end"
    (is (not (completely-in-range? "10.0.0.2/30" "10.0.0.3/32")))))

(deftest overlap?-test
  (testing "that working out whether two ranges overlap returns false when they don't overlap at all"
    (is (not (overlap? "9.0.0.0/8" "10.0.0.0/8")))
    (is (not (overlap? "10.0.0.0/8" "9.0.0.0/8"))))

  (testing "that working out whether two ranges overlap returns false when they don't overlap at all"
    (is (overlap? "10.0.0.255/30" "10.0.0.255/32"))
    (is (overlap? "10.0.0.255/21" "10.0.0.255/21"))
    (is (overlap? "10.140.48.0/20" "10.140.48.0/20"))
    (is (overlap? "10.1.0.0/20" "10.1.0.0/21"))
    (is (overlap? "10.1.0.0/21" "10.1.0.0/20")))

  (testing "that working out whether two ranges overlap returns false when they don't overlap at all"
    (is (overlap? "10.0.0.2/30" "10.0.0.3/32"))))
