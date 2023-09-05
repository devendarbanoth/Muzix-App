package com.group6.Muzix.Services;

import com.group6.Muzix.Beans.Favourite;
import com.group6.Muzix.Repository.FavouriteRepository;
import com.group6.Muzix.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FavouriteService {
    @Autowired
    private FavouriteRepository favouriteRepository;

//    public Favourite addfavourite(Favourite f){
//        Favourite favouriteNew=  favouriteRepository.save(f);
//        return favouriteNew;
//    }
    public ResponseEntity<?> addNewFavourite(Favourite favourite) {
        try{
            if(Objects.nonNull(favouriteRepository.findOneByMusicId(favourite.getMusicId()))){
                throw new CustomException("Music is already added");
            }
            Favourite newFavourite = favouriteRepository.save(favourite);
            return new ResponseEntity<>(newFavourite,HttpStatus.CREATED);

        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    public List<Favourite> getAll(){
//        List<Favourite> favourites = favouriteRepository.findAll();
//        return favourites;
//    }

    public ResponseEntity<?> getAllFavouriteMusic(Integer userId) {
        try{
            List<Favourite> favourites = favouriteRepository.findAllByUserId(userId);
            if(favourites.isEmpty()){
                throw new CustomException("No music added to favourite playlist");
            }else{
                return ResponseEntity.ok(favourites);
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    public boolean deletefav(int musicId){,
//        favouriteRepository.deleteById(musicId);
//        return true;
//    }

    public ResponseEntity<?> removeMusicFromFavourite(int favId) {
        try{
            if(Objects.nonNull(favouriteRepository.findOneById(favId))){
                favouriteRepository.deleteById(favId);
                return ResponseEntity.ok("MusicId:" + favId + " removed from favourites");
            }else{
                throw new CustomException("MusicId:" + favId + " not found in favourites");
            }
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
