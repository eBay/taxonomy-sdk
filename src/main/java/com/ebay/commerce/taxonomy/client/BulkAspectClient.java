/*
 *  Copyright (c) 2020 eBay Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.ebay.commerce.taxonomy.client;

import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.AccessToken;
import com.ebay.commerce.taxonomy.constants.Constants;
import com.ebay.commerce.taxonomy.constants.Environment;
import com.ebay.commerce.taxonomy.exceptions.ClientException;
import com.ebay.commerce.taxonomy.exceptions.OAuthTokenException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class BulkAspectClient {

    private OkHttpClient client = new OkHttpClient();

    private OAuth2Api oAuthClient = new OAuth2Api();

    public InputStream getDataStream(Environment environment, String categoryTreeId, String token) throws IOException {
        if(token==null)token = fetchToken(environment);
        String uri = String.format(ClientConstants.getEndpointForEnvironment(environment),categoryTreeId);
        Request request = new Request.Builder().url(uri)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        if(response.code() != HttpStatus.OK.value())
            throw new ClientException(String.format("Data retrieval failed with %s  for %s ",response.code(),uri));
        return response.body().byteStream();
    }

    private String fetchToken(Environment environment)  {
        com.ebay.api.client.auth.oauth2.model.Environment authEnvironment = environment==Environment.PRODUCTION?com.ebay.api.client.auth.oauth2.model.Environment.PRODUCTION:
                com.ebay.api.client.auth.oauth2.model.Environment.SANDBOX;
        Optional<AccessToken> accessToken = null;
        try {
            accessToken = oAuthClient.getApplicationToken(authEnvironment, Constants.APPLICABLE_SCOPES).getAccessToken();
        } catch (IOException e) {
            throw new OAuthTokenException("Token retrieval failure for environment "+environment, e);
        }
        if(accessToken.isPresent())
            return "bearer "+accessToken.get().getToken();
        throw new OAuthTokenException("Token retrieval failure fpr environment "+environment+".Please check credentials.");

    }

}
