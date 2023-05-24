CREATE VIEW cinescope.seriesOnLists AS
SELECT sl.slid, sl.userid, sl.name, su.stmdbid, su.state
FROM cinescope.seriesLists sl
         INNER JOIN cinescope.serieList s ON sl.slid = s.slid
         INNER JOIN cinescope.seriesUserData su ON s.stmdbid = su.stmdbid;

select * from cinescope.seriesOnLists where stmdbid = 2316 AND userid=2;

CREATE VIEW cinescope.WatchedEpisodes AS
    SELECT sud.eplid, sud.stmdbid, ep.epimdbid, epdata.name, epdata.season, epdata.episode
    FROM cinescope.seriesuserdata sud
    INNER JOIN cinescope.watchedepisodelist ep ON sud.eplid = ep.eplid
    INNER JOIN cinescope.episodesdata epdata ON ep.epimdbid = epdata.epimdbid;

select * from cinescope.WatchedEpisodes



drop view cinescope.seriesOnLists;
drop view cinescope.WatchedEpisodes;