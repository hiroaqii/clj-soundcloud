(ns soundcloud.core
  (:require '[clojure.string :as str])
　(:require '[clj-http.client :as client]))

(def soundcloud-base-url "api.soundcloud.com")

(defmacro def-soundcloud-fn [fn-name req-method req-url]
  (let [tmp (re-seq  #"\{\:(\w+)\}" req-url)
	params (vec (map #(first %) tmp))
	str-args (vec (map #(second %) tmp))
	args (vec (map #(read-string %) str-args))]
    `(defn ~fn-name [~@args param-map#]
       (let [url-resource# (reduce
                             (fn [url# [k# v#]](str/replace url# k# (str v#)))
                                ~req-url (into [] (zipmap ~params ~args)))
	     query-param# (client/generate-query-string param-map#)]
         (client/request {:scheme "https"
	                  :request-method ~req-method
	                  :uri (str soundcloud-base-url url-resource#)
	                  :query-string query-param#})))))

;; /users
(def-soundcloud-fn users :get "/users.json")
(def-soundcloud-fn user :get "/users/{:user_id}.json")
(def-soundcloud-fn user-tracks :get  "/users/{:user_id}/tracks.json")
(def-soundcloud-fn user-playlists :get "/users/{:user_id}/playlists.json")
(def-soundcloud-fn user-followings :get "/users/{:user_id}/followings.json")
(def-soundcloud-fn user-following :get "/users/{:user_id}/followings/{:contact_id}.json")
(def-soundcloud-fn user-followers :get "/users/{:user_id}/followers.json")
(def-soundcloud-fn user-follower :get "/users/{:user_id}/followers/{:contact_id}.json")
(def-soundcloud-fn user-comments :get "/users/{:user_id}/comments.json")
(def-soundcloud-fn user-favorites :get "/users/{:user_id}/favorites.json")
(def-soundcloud-fn user-favorite :get "/users/{:user_id}/favorites/{:track_id}")
(def-soundcloud-fn user-groups :get "/users/{:user_id}/groups.json")

;; /playlists
(def-soundcloud-fn playlists :get "/playlists.json")
(def-soundcloud-fn playlist :get "/playlists/{:playlist_id}.json") 

;; /groups
(def-soundcloud-fn groups :get "/groups.json")
(def-soundcloud-fn group :get "/groups/{group_id}.json")
(def-soundcloud-fn group-moderators :get "/groups/{:group_id}/moderators.json")
(def-soundcloud-fn group-members :get "/groups/{:group_id}/members.json")
(def-soundcloud-fn group-contributors :get "/groups/{:group_id}/contributors.json")
(def-soundcloud-fn group-users :get "/groups/{:group_id}/users.json")
(def-soundcloud-fn group-tracks :get "/groups/{:group_id}/tracks.json")

;; /comments
(def-soundcloud-fn comments :get "/comments.json")
(def-soundcloud-fn comment :get "/comments/{comment_id}.json")

;; /track
(def-soundcloud-fn tracks :get "tracks.json")
(def-soundcloud-fn track :get "tracks/{track_id}.json")
(def-soundcloud-fn track-comments :get "/tracks/{track_id}/comments.json")
(def-soundcloud-fn track-favoriters :get "/tracks/{track_id}/favoriters.json")
(def-soundcloud-fn track-favoriter-user :get "/tracks/{track_id}/favoriters/{user-id}")

;; /me
(def-soundcloud-fn me :get "/me.json")
(def-soundcloud-fn my-tracks :get "/me/tracks.json")
(def-soundcloud-fn my-playlists :get "/me/playlists.json")
(def-soundcloud-fn my-followings :get "/me/followings.json")
(def-soundcloud-fn my-followers :get "/me/followers.json")
(def-soundcloud-fn my-comments :get"/me/comments.json")
(def-soundcloud-fn my-favorites :get "/me/favorites.json")
(def-soundcloud-fn my-groups :get "/me/groups.json")

;; ex
;; (user "username" {:clientid "xxxxx"})
;; (me {:oauth_token "xxxxxx"})