package com.trikot.sample.repositories.datasources.queries

import com.trikot.sample.models.CountryResponse
import com.trikot.sample.models.DataResponse
import com.mirego.trikot.graphql.AbstractGraphqlQuery

class SampleQuery(countryCode: String) :
    AbstractGraphqlQuery<DataResponse<CountryResponse>>(
        DataResponse.serializer(
            CountryResponse.serializer())) {
    override val variables: Map<String, String>? = mapOf(
        "countryCode" to countryCode
    )
    override val query: String = """
query(${'$'}countryCode: String!) {
  country(code: ${'$'}countryCode) {
    __typename
    name
  }
}}"""
}
