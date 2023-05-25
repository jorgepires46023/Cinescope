CREATE OR REPLACE VIEW cinescope.movieOnLists AS
SELECT ml.mlid, ml.userid, ml.name, m.mtmdbid
    FROM cinescope.movieslists ml
    JOIN cinescope.movielist m ON ml.mlid = m.mlid;


select * from cinescope.movieOnLists;

drop view cinescope.movieOnLists;