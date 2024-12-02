package com.example.festie_backend.repository;

import com.example.festie_backend.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
