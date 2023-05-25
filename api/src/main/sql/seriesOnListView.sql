CREATE VIEW cinescope.seriesOnLists AS
SELECT sl.slid, sl.userid, sl.name, s.stmdbid
FROM cinescope.seriesLists sl
         INNER JOIN cinescope.serieList s ON sl.slid = s.slid;

select * from cinescope.seriesOnLists where stmdbid = 2316 and userid = 1;

CREATE VIEW cinescope.WatchedEpisodes AS
    SELECT sud.eplid, sud.stmdbid, ep.epimdbid, epdata.name, epdata.season, epdata.episode
    FROM cinescope.seriesuserdata sud
    INNER JOIN cinescope.watchedepisodelist ep ON sud.eplid = ep.eplid
    INNER JOIN cinescope.episodesdata epdata ON ep.epimdbid = epdata.epimdbid;

select * from cinescope.WatchedEpisodes



drop view cinescope.seriesOnLists;
drop view cinescope.WatchedEpisodes;