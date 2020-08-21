package google

import com.google.api.gax.core.GoogleCredentialsProvider
import com.google.auth.Credentials
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.OAuth2Credentials
import com.google.common.collect.ImmutableList

class SBCredentialsProvider(val accessToken: AccessToken) : GoogleCredentialsProvider() {

    override fun getScopesToApply(): MutableList<String> {
        return ImmutableList.builder<String>().add("https://www.googleapis.com/auth/cloud-platform")
                .build()
    }

    override fun toBuilder(): Builder {
        return object : Builder() {
            override fun getScopesToApply(): MutableList<String> {
                return ImmutableList.of()
            }

            override fun setJwtEnabledScopes(`val`: MutableList<String>?): Builder {
                return this
            }

            override fun setScopesToApply(`val`: MutableList<String>?): Builder {
                return this
            }

            override fun getJwtEnabledScopes(): MutableList<String> {
                return ImmutableList.of()
            }
        }
    }

    override fun getJwtEnabledScopes(): MutableList<String> {
        return ImmutableList.of()
    }

    override fun getCredentials(): Credentials {
        return OAuth2Credentials.create(accessToken)
    }


}