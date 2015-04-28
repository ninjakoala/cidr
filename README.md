```clojure
[com.ninjakoala/cidr "1.0.0"]
```

```clojure
(ns your.namespace
  (:require [cidr.core :as cidr]))

(cidr/in-range? "192.168.0.1" "192.168.0.0/24") ; => true
(cidr/in-range? "192.167.0.1" "192.168.0.0/24") ; => false

(cidr/completely-in-range? "192.168.0.0/24" "192.168.0.0/16") ; => true

(cidr/overlap? "192.168.0.1/25" "192.168.0.1/24") ; => true
  
```

# cidr

General utilities for dealing with [CIDR](http://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing).

## Acknowledgements

Massive thanks must go to [Keith Irwin](https://github.com/zentrope) for allowing me to lift the [original code](https://github.com/zentrope/match-expr/blob/master/src/match_expr/impl/cidr.clj) and package it up in to a library.

## License

Copyright © 2015 Neil Prosser

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
