create schema cinescope;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table cinescope.users (
    userId int primary key GENERATED ALWAYS AS IDENTITY,
    token varchar(100) DEFAULT uuid_generate_v4(),
    name varchar(100) NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,                         -- encrypted
    state varchar(10) NOT NULL check (state in ('Active', 'Inactive'))
);

create table cinescope.moviesData (
    mimdbid varchar(100) primary key,
    mtmdbid varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL
);

create table cinescope.movieUserData (
    mimdbid varchar(100) NOT NULL REFERENCES cinescope.moviesData(mimdbid),
    userId int NOT NULL REFERENCES cinescope.users(userid),
    state varchar(20) NOT NULL check ( state in ('PTW', 'WATCHED'))
);

create table cinescope.moviesLists (
    mlid int primary key GENERATED ALWAYS AS IDENTITY,
    userid int references cinescope.users(userid),
    name varchar(100) NOT NULL
);

create table cinescope.movieList (
    mimdbid varchar(100) NOT NULL REFERENCES cinescope.moviesData(mimdbid),
    mlid int NOT NULL REFERENCES cinescope.moviesLists
);

create table cinescope.seriesData (
    simdbid varchar(100) primary key,
    stmdbid varchar(100) UNIQUE NOT NULL,
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL
);

create table cinescope.episodesData (
    epimdbid varchar(100) primary key,
    stmdbid varchar(100) NOT NULL references cinescope.seriesData(stmdbid),
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL,
    season int NOT NULL,
    episode int not null
);

create table cinescope.seriesUserData (
    eplid int primary key GENERATED ALWAYS AS IDENTITY,
    simdbid varchar(100) NOT NULL REFERENCES cinescope.seriesData(simdbid),
    userid int NOT NULL REFERENCES cinescope.users,
    state varchar(100) NOT NULL check (state in ('PTW', 'Watched', 'Watching'))
);

create table cinescope.watchedEpisodeList (
    eplid int NOT NULL REFERENCES cinescope.seriesUserData,
    epimdbid varchar(100) NOT NULL REFERENCES cinescope.episodesData(epimdbid)
);

create table cinescope.seriesLists (
    slid int primary key GENERATED ALWAYS AS IDENTITY,
    userid int NOT NULL references cinescope.users(userid),
    name varchar(100) NOT NULL
);

create table cinescope.serieList (
    slid int NOT NULL REFERENCES cinescope.seriesLists(slid),
    simdbid varchar(100) NOT NULL REFERENCES cinescope.seriesData
);

