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
    mtmdbid INT primary key,
    mimdbid varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL
);

create table cinescope.movieUserData (
    mtmdbid INT NOT NULL REFERENCES cinescope.moviesData(mtmdbid),
    userId int NOT NULL REFERENCES cinescope.users(userid),
    primary key (mtmdbid, userId),
    state varchar(20) NOT NULL check ( state in ('PTW', 'Watched'))
);

create table cinescope.moviesLists (
    mlid int primary key GENERATED ALWAYS AS IDENTITY,
    userid int references cinescope.users(userid),
    name varchar(100) NOT NULL
);

create table cinescope.movieList (
    mtmdbid INT NOT NULL REFERENCES cinescope.moviesData(mtmdbid),
    mlid int NOT NULL REFERENCES cinescope.moviesLists(mlid),
    primary key (mtmdbid, mlid)
);

create table cinescope.seriesData (
    stmdbid INT primary key,
    simdbid varchar(100) UNIQUE NOT NULL,
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL
);

create table cinescope.episodesData (
    epID int primary key GENERATED ALWAYS AS IDENTITY,
    stmdbid INT NOT NULL references cinescope.seriesData(stmdbid),
    epimdbid varchar (100),
    name varchar(100) NOT NULL,
    image varchar(100) NOT NULL,
    season int NOT NULL,
    episode int not null
);

create table cinescope.seriesUserData (
    eplid int primary key GENERATED ALWAYS AS IDENTITY,
    stmdbid INT NOT NULL REFERENCES cinescope.seriesData(stmdbid),
    userid int NOT NULL REFERENCES cinescope.users(userId),
    state varchar(100) NOT NULL check (state in ('PTW', 'Watched', 'Watching'))
);

create table cinescope.watchedEpisodeList (
    eplid int NOT NULL REFERENCES cinescope.seriesUserData(eplid),
    epid int NOT NULL REFERENCES cinescope.episodesData(epid),
    primary key (eplid, epid)
);

create table cinescope.seriesLists (
    slid int primary key GENERATED ALWAYS AS IDENTITY,
    userid int NOT NULL references cinescope.users(userid),
    name varchar(100) NOT NULL
);

create table cinescope.serieList (
    slid int NOT NULL REFERENCES cinescope.seriesLists(slid),
    stmdbid INT NOT NULL REFERENCES cinescope.seriesData(stmdbid)
);

