CREATE TABLE IF NOT EXISTS fcm (
	"id" bigserial NOT NULL PRIMARY KEY,
	userId int NOT NULL,
	sessionId text NOT NULL,
	"token" text NOT NULL
);