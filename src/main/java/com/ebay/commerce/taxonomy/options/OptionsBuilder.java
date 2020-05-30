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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

@Component
public class OptionsBuilder {
    public Options build() {
        return new Options() {
            {
                addOption(Option.builder("h").required(false).hasArg(false).longOpt("help").build());
                addOption(Option.builder("e").required(false).hasArg(true).longOpt("environment").desc("Environment - SANDBOX or PRODUCTION").build());
                addOption(Option.builder("ct").required(false).hasArg(true).longOpt("category_tree_id").desc("Category Tree Id").build());
                addOption(Option.builder("p").required(false).hasArg(true).longOpt("previous_file").desc("Previous file").build());
                addOption(Option.builder("c").required(false).hasArg(true).longOpt("current_file").desc("Current file").build());
                addOption(Option.builder("l").required(false).hasArg(false).longOpt("latest").desc("Use latest. Requires oauth credentials").build());
                addOption(Option.builder("o").required(false).hasArg(true).longOpt("out").desc("Output directory").build());
                addOption(Option.builder("t").required(false).hasArg(true).longOpt("token").desc("Client credentials token").build());
                addOption(Option.builder("cc").required(false).hasArg(true).longOpt("client_config_file").desc("Client credentials.").build());
            }
        };
    }
}
