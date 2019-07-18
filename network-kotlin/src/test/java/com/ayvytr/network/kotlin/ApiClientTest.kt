package com.ayvytr.network.kotlin

import org.junit.Assert
import org.junit.Test

/**
 * @author admin
 */

class ApiClientTest {
    @Test
    fun testInit() {
        val apiClient = ApiClient.instance
        apiClient.init("http://google.com")

        Assert.assertNotNull(apiClient)

    }
}