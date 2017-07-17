# shutdown

A tiny library to manage a [clojure](http://clojure.org) application's
jvm shutdown hooks. Inspired by [duct](https://github.com/duct-framework/duct)

## Usage

```clojure
(require '[shutdown.core :as shutdown])

;; To add a 0-argument function to be executed before your app's runtime shuts
;; down (normally):
(shutdown/add-hook! ::descriptive-name #(println "It's about to go down!"))

;; In case you later change your mind:
(shutdown/remove-hook ::descriptive-name)
```

## License

Copyright © 2017 ben lamothe

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
