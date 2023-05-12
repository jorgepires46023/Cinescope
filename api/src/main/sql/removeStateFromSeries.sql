create or replace procedure cinescope.removeStateFromSerie(serie_id int, user_id int)
    language plpgsql
as
$$
declare series_lists_ids record;
begin
    for series_lists_ids in select * from cinescope.serieslists where userid = user_id loop
            DELETE from cinescope.serielist where stmdbid = serie_id and slid = series_lists_ids.slid;
        end loop;
    DELETE from cinescope.watchedepisodelist
        where ( eplid =
            (select eplid from cinescope.seriesuserdata where stmdbid = serie_id and userid = user_id));
    delete from cinescope.seriesuserdata where stmdbid = serie_id and userid = user_id;
end;

$$;