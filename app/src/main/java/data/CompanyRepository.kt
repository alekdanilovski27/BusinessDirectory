package com.example.businessdir.data

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator

object CompanyRepository {

    suspend fun getCompaniesByCategory(category: String, search: String = ""): List<Company> {
        val column = when (category) {
            "services" -> "is_services"
            "fun" -> "is_fun"
            "industry" -> "is_industry"
            "education" -> "is_education"
            else -> "is_services"
        }

        return SupabaseClient.client.from("companies").select {
            filter {
                eq(column, true)
                if (search.isNotEmpty()) {
                    ilike("name", "%$search%")
                }
            }
        }.decodeList<Company>()
    }

    suspend fun insertCompany(company: Company) {
        SupabaseClient.client.from("companies").insert(company)
    }
}

