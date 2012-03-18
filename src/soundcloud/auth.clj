(ns soundcloud.oauth
  (:require [clj-oauth2.client :as oauth2]))

(def soundcloud-auth-base-url "https://soundcloud.com/connect")
(def soundcloud-access-token-base-url "https://api.soundcloud.com/oauth2/token")

;;todo