package com.example.cinescope.testutils

import com.example.cinescope.services.exceptions.IllegalPathInitializationException
import com.example.cinescope.services.exceptions.IllegalPathVariablesException
import com.example.cinescope.services.exceptions.MismatchingPathVariablesException
import com.example.cinescope.utils.joinPath
import com.example.cinescope.utils.joinPathWithVariables
import com.example.cinescope.utils.validatePathVariables
import org.junit.Assert
import org.junit.Test
import java.net.URL

class UtilsURLTests {
    private val DOMAIN_URL = "http://cinescope.com"
    private val simplePath = "/testURLPath"
    private val wrongSimplePath = "testURLPath"
    private val expectedJoinPathUrl = URL(DOMAIN_URL+simplePath)
    private val multiplePathVariables = "/series/{id}/list/{lid}"
    private val multipleWrongPathVariables = "/series/{id}/list/{id}" //Cannot have 2 path variables with the same name
    private val expectedJoinPathWithVariables = URL("$DOMAIN_URL/series/id1/list/id2")


    @Test
    fun `joinPath method from utilsUrl correct implementation`(){
        //Arrange
        val initialURL = URL(DOMAIN_URL)
        //Act
        val actual = initialURL.joinPath(simplePath)

        //Assert
        Assert.assertEquals(expectedJoinPathUrl, actual)
    }

    @Test(expected = IllegalPathInitializationException::class)
    fun `joinPath method throws IllegalPathInitializationException when path is not correctly initialized`(){
        //Arrange
        val initialURL = URL(DOMAIN_URL)
        //Act
        val actual = initialURL.joinPath(wrongSimplePath)
    }

    @Test
    fun `validatePathVariables with equal elements`(){
        //Arrange
        val list = listOf("{id1}","{id1}","{id2}")
        val variablesList = listOf("id1","id2","id3")

        //Act
        val actualListSize = list.size
        val actualDistinctCount = list.distinct().count()
        val actual = validatePathVariables(list, variablesList)

        //Assert
        Assert.assertEquals(3,actualListSize)
        Assert.assertEquals(2, actualDistinctCount)
        Assert.assertEquals(false, actual)
    }

    @Test
    fun `validatePathVariables with all different elements`(){
        //Arrange
        val list = listOf("{id1}","{id2}","{id3}")
        val variablesList = listOf("id1","id2","id3")

        //Act
        val actualListSize = list.size
        val actualDistinctCount = list.distinct().count()
        val actual = validatePathVariables(list, variablesList)

        //Assert
        Assert.assertEquals(3,actualListSize)
        Assert.assertEquals(3, actualDistinctCount)
        Assert.assertEquals(true, actual)
    }
    @Test
    fun `validatePathVariables with different list sizes but same number of variables`(){
        //Arrange
        val list = listOf("{id1}","{id2}","id3")
        val variablesList = listOf("id1","id2")

        //Act
        val actualListSize = list.size
        val actualDistinctCount = list.distinct().count()
        val actual = validatePathVariables(list, variablesList)

        //Assert
        Assert.assertEquals(3,actualListSize)
        Assert.assertEquals(3, actualDistinctCount)
        Assert.assertEquals(true, actual)
    }

    @Test(expected = MismatchingPathVariablesException::class)
    fun `validatePathVariables with different number of path variables and list variables`(){
        //Arrange
        val list = listOf("id1","id2","id3")
        val variablesList = listOf("id1","id2")

        //Act
        validatePathVariables(list, variablesList)
    }

    @Test(expected = IllegalPathVariablesException::class)
    fun `joinPath method throws IllegalPathVariablesException when multiple variables are equal`(){
        //Arrange
        val initialURL = URL(DOMAIN_URL)
        //Act
        initialURL.joinPathWithVariables(multipleWrongPathVariables, listOf("id1", "id2"))
    }

    @Test
    fun `joinPath method when add multiple correct variables`(){
        //Arrange
        val initialURL = URL(DOMAIN_URL)
        //Act
        val actual = initialURL.joinPathWithVariables(multiplePathVariables, listOf("id1", "id2"))
        //Assert
        Assert.assertEquals(expectedJoinPathWithVariables, actual)
    }
}