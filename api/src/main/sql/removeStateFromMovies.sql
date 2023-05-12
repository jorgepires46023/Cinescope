create or replace procedure cinescope.removeStateFromMovie(movie_id int, user_id int)
language plpgsql
as
$$
    declare movies_lists_ids record;
begin
    for movies_lists_ids in select * from cinescope.movieslists where userid = :userid loop
        DELETE from cinescope.movielist where mtmdbid = movie_id and mlid = movies_lists_ids.mlid;
        end loop;
    delete from cinescope.movieuserdata where mtmdbid = movie_id and userid = user_id;
end;

$$;

call cinescope.removestatefrommovie(634649, 1);