package com.yuhelper.core.repo;


import com.yuhelper.core.model.UserInfo;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserInfoRepository extends CustomJPARepository<UserInfo, BigInteger> {
}
