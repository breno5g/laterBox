ALTER TABLE links DROP CONSTRAINT links_url_key;

ALTER TABLE links ADD CONSTRAINT links_user_id_url_unique UNIQUE (user_id, url);
