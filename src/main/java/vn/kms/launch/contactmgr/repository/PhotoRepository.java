package vn.kms.launch.contactmgr.repository;

import java.io.ObjectInputStream.GetField;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.image.*;
import vn.kms.launch.contactmgr.service.PhotoService;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
 * Created by diule on April, 14
 * */

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    /**
     * Query function getById;
     * return 0,
     * @param ids
     * @return
     */
//	@Modifying
//	@Transactional
//	@Query("select id from Image where id in (:ids)")
//	//int get;

    /**
     * Query function getAll()
     * return getAllIds
     * @param ids
     * @return
     */

    /**
     * Query function Create file upload;
     * return
     * @param
     * @return
     */
    /*@Modifying
    @Transactional
	@Query("insert from Contact where id in (:ids)")
	int deleteContacts(@Param("ids")int... ids);*/

}
