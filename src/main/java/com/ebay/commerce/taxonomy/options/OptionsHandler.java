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

package com.ebay.commerce.taxonomy.options;

import com.ebay.api.client.auth.oauth2.CredentialUtil;
import com.ebay.commerce.taxonomy.context.Control;
import com.ebay.commerce.taxonomy.context.ExecutionContext;
import com.ebay.commerce.taxonomy.exceptions.OptionsValidationException;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class OptionsHandler {

    @Autowired
    private OptionsBuilder optionsBuilder;

    @Autowired
    private OptionsHandler optionsHandler;

    private Logger logger = LoggerFactory.getLogger(OptionsHandler.class);

    public void handleArguments(String[] args) {
        try {
            if(args.length==0) {
                throw new OptionsValidationException("Please use -h to understand arguments supported");
            }
            optionsHandler.setExecutionContext(args);
        } catch(ParseException | OptionsValidationException e) {
            logger.error(e.getMessage());
        }
    }

    private void setExecutionContext(String[] args) throws ParseException {
        Options options = optionsBuilder.build();
        CommandLine commandLine = new DefaultParser().parse(options, args);
        if (commandLine.hasOption("h")) {
            processHelpOption(options);
            return;
        }
        preValidate(commandLine);
        ExecutionContext.create(new Control(
                commandLine.getOptionValue("e"),
                commandLine.getOptionValue("ct"),
                commandLine.getOptionValue("p"),
                commandLine.getOptionValue("c"),
                commandLine.getOptionValue("o"),
                commandLine.hasOption("l") == Boolean.TRUE,
                commandLine.getOptionValue("t")));
        if(requiresAuthTokenClientCredentials()) {
            setOauthClientCredentials(commandLine.getOptionValue("cc"));
        }

    }

    private void setOauthClientCredentials(String clientCredentialsConfigFile) {
        try {
            CredentialUtil.load(new FileInputStream(clientCredentialsConfigFile));

        } catch (IOException e) {
            throw new OptionsValidationException("Failed to initialize oauth credentials from "+clientCredentialsConfigFile);
        }
    }

    private boolean requiresAuthTokenClientCredentials() {
        return ExecutionContext.useLatest() && ExecutionContext.bearerToken()==null;
    }


    private void preValidate(CommandLine commandLine) {
        File file = new File(commandLine.getOptionValue("o"));
        if (!file.isDirectory()) {
            throw new OptionsValidationException(String.format("Specified directory %s does not exist", commandLine.getOptionValue("o")));

        }
        if(commandLine.hasOption("l")) {
            logger.info("Value of current file will be ignored and latest file downloaded will be used for comparison");
            if(!commandLine.hasOption("cc") && !commandLine.hasOption("t"))
                throw new OptionsValidationException("For download latest option please specify path to credentials file OR provide valid token.");
        }
    }


    private void processHelpOption(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptionComparator(null);
        formatter.printHelp(" java -jar aspects-sdk-0.0.1-SNAPSHOT.jar", options, true);
    }


}
