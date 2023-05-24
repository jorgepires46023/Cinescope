CREATE OR REPLACE VIEW cinescope.movieOnLists AS
SELECT ml.mlid, ml.userid, ml.name, m.mtmdbid, mu.state
    FROM cinescope.movieslists ml
    INNER JOIN cinescope.movielist m ON ml.mlid = m.mlid
    INNER JOIN cinescope.movieuserdata mu ON m.mtmdbid = mu.mtmdbid;


select * from cinescope.movieOnLists;

drop view cinescope.movieOnLists;