package com.pickdate.iam

import spock.lang.Ignore
import spock.lang.Specification


// TODO: finish this
@Ignore
class UserServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def userService = new UserService(userRepository)

    def "should create user"() {

//        when:
//        userService.createUser()


    }
}
