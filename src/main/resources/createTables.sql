create table if not exists guilds(gid text unique, prefix text, customPrefix boolean);
create table if not exists users(uid text unique, coins bigint);