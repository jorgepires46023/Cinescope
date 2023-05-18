package pt.isel.ps.cinescope.services


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.testWithTransactionManagerAndRollback
import pt.isel.ps.cinescope.utils.SHA256Encoder
import kotlin.test.assertFailsWith


class UsersServicesTests {
    companion object {
        val encoder = SHA256Encoder()
    }

    @Test
    fun `create user`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val res = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")

            assertNotNull(res)
        }
    }

    @Test
    fun `create user without name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.createUser(null, "jorgepires@scp.pt", "SCP é o maior")
            }
        }
    }

    @Test
    fun `create user without email`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.createUser("Jorge Pires", null, "SCP é o maior")
            }
        }
    }

    @Test
    fun `create user without password`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", null)
            }
        }
    }

    @Test
    fun `remove user with id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "SCP é o maior")
            assertNotNull(id)

            usersServices.removeUser(id)

            val user = usersServices.getUserById(id)
            assertNotNull(user)
            assertEquals("Inactive", user?.state.toString())
        }
    }

    @Test
    fun `remove user without id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.removeUser(null)
            }
        }
    }

    @Test
    fun `Get User By its Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Pinto", "joaopinto@scp.pt", "O SCP é o meu amor")
            assertNotNull(id)

            val user = usersServices.getUserById(id)

            assertNotNull(user)
            assertEquals("João Pinto", user?.name)
            assertEquals("joaopinto@scp.pt", user?.email)
            assertTrue(encoder.validateInfo("O SCP é o meu amor", user?.password))
        }
    }

    @Test
    fun `Get User without its Id`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.getUserById(null)
            }
        }
    }

    @Test
    fun `Get User that don't exist`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<NotFoundException> {
                usersServices.getUserById(38547981)
            }
        }
    }

 /*   @Test
    fun `Edit the password of an User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            usersServices.editUser(id, "João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR de Portugal")

            val user = usersServices.getUserById(id)
            assertNotNull(user)

            assertEquals("João Balsinha", user?.name)
            assertEquals("joaobalsinha@slb.pt", user?.email)
            assertTrue(encoder.validateInfo("O Benfica é o MAIOR de Portugal", user?.password))
        }
    }

    @Test
    fun `Edit the name of an User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            usersServices.editUser(id, "JoaoBalsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            val user = usersServices.getUserById(id)
            assertNotNull(user)

            assertEquals("JoaoBalsinha", user?.name)
            assertEquals("joaobalsinha@slb.pt", user?.email)
            assertTrue(encoder.validateInfo("O Benfica é o MAIOR", user?.password))
        }
    }

    @Test
    fun `Edit the email of an User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            usersServices.editUser(id, "João Balsinha", "jbalsinha@slb.pt", "O Benfica é o MAIOR")

            val user = usersServices.getUserById(id)
            assertNotNull(user)

            assertEquals("João Balsinha", user?.name)
            assertEquals("jbalsinha@slb.pt", user?.email)
            assertTrue(encoder.validateInfo("O Benfica é o MAIOR", user?.password))
        }
    }

    @Test
    fun `Edit all Info of an User`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            usersServices.editUser(id, "JoaoBalsinha", "jbalsinha@slb.pt", "O Benfica é o MAIOR de Portugal")

            val user = usersServices.getUserById(id)
            assertNotNull(user)

            assertEquals("JoaoBalsinha", user?.name)
            assertEquals("jbalsinha@slb.pt", user?.email)

            assertTrue(encoder.validateInfo("O Benfica é o MAIOR de Portugal", user?.password))
        }
    }

    @Test
    fun `Edit the Info of an User, without a name`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, null,"joaobalsinha@slb.pt" ,"O Benfica é o MAIOR" )
            }

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, "","joaobalsinha@slb.pt" , "O Benfica é o MAIOR")
            }
        }
    }

    @Test
    fun `Edit the Info of an User, without an email`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, "João Balsinha", null,"O Benfica é o MAIOR" )
            }

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, "João Balsinha", null, "O Benfica é o MAIOR")
            }
        }
    }

    @Test
    fun `Edit the Info of an User, without a password`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("João Balsinha", "joaobalsinha@slb.pt", "O Benfica é o MAIOR")

            assertNotNull(id)

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, "João Balsinha", "joaobalsinha@slb.pt", null)
            }

            assertFailsWith<BadRequestException> {
                usersServices.editUser(id, "João Balsinha", "joaobalsinha@slb.pt", "")
            }
        }
    }*/

    @Test
    fun `Get User By Token`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            val id = usersServices.createUser("Jorge Pires", "jorgepires@scp.pt", "Amo o SCP")
            assertNotNull(id)

            val user = usersServices.getUserById(id)
            assertNotNull(user)

            val userByToken = usersServices.getUserByToken(user?.token.toString())
            assertNotNull(userByToken)

            assertEquals(user?.name, userByToken?.name)
            assertEquals(user?.email, userByToken?.email)
            assertEquals(user?.password, userByToken?.password)
        }
    }

    @Test
    fun `Get User By Token, without the token`() {
        testWithTransactionManagerAndRollback { transactionManager ->

            val usersServices = UsersServices(encoder, transactionManager)

            assertFailsWith<BadRequestException> {
                usersServices.getUserByToken(usertoken = null)
            }

            assertFailsWith<BadRequestException> {
                usersServices.getUserByToken(usertoken = "")
            }
        }
    }


}