package com.example.businessdir.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://uwlbtxtnlkxszdntmzaa.supabase.co",
        supabaseKey = "sb_publishable_EMc4Ie0-irLvwD7513b9Dw_UItIK9JY"
    ) {
        install(Postgrest)
    }
}

