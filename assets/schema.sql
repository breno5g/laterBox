CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username varchar(50) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE links (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    url TEXT NOT NULL UNIQUE,
	title TEXT NOT NULL,
	description TEXT NOT NULL,
	is_read BOOLEAN DEFAULT FALSE,
	is_favorite BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
	read_at TIMESTAMPTZ,
	user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_links_user_id ON links(user_id);
CREATE INDEX idx_links_is_read ON links(is_read);
CREATE INDEX idx_links_is_favorite ON links(is_favorite);
CREATE INDEX idx_links_user_read_favorites ON links(user_id, is_read, is_favorite);

CREATE TABLE tags (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name varchar(42) NOT NULL,
	color char(9) NOT NULL,
);

CREATE TABLE link_tag (
	link_id uuid NOT NULL,
	tag_id uuid NOT NULL,
	PRIMARY KEY(link_id, tag_id),
    FOREIGN KEY (link_id) REFERENCES  links(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES  tags(id) ON DELETE CASCADE
);

CREATE INDEX idx_link_tags_link_id ON link_tag(link_id);
CREATE INDEX idx_link_tags_tag_id ON link_tag(tag_id);