package com.cad.demo.dao;

import com.cad.demo.entity.User;
import com.cad.demo.entity.vo.UserVO;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    int addUser(User user) throws DataAccessException;


    List<UserVO> getUsers(User user) throws DataAccessException;


    int deleteUser(Integer id) throws DataAccessException;


    int updateUser(User user) throws DataAccessException;

}
