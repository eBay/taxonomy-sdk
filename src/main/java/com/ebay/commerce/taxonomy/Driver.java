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

package com.ebay.commerce.taxonomy;

import com.ebay.commerce.taxonomy.options.OptionsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//--prev_file=/Users/snbanerjee/tmp/taxonomy_aspects_0_121_20200406091617.gz --current_file=/Users/snbanerjee/tmp/taxonomy_aspects_0_121_20200406091617.gz --out=/Users/snbanerjee/tmp/
//--prev_file=/Users/snbanerjee/tmp/old.gz --current_file=/Users/snbanerjee/tmp/new.gz --out=/Users/snbanerjee/tmp/
//-cc /Users/snbanerjee/tmp/ebay-config.yaml -l -ct 0 -e test --prev_file=/Users/snbanerjee/tmp/taxonomy_aspects_0_121_20200406091617.gz --current_file=/Users/snbanerjee/tmp/latest.gz --out=/Users/snbanerjee/tmp/ --t=v^1.1#i^1#p^1#r^0#f^0#I^3#t^H4sIAAAAAAAAAO1Ya2wUVRTu9hnE0gRNFQSyDlhAM7t3Xrs7A7th6bawgW4r2xZaJWUed8rQ2ZnpzN22GxNSC0HCD0PwFV/QEI0m9UUIP4xJlRBJICYIqMREjIFgBGNQKFT9gzO72+22mlZhf+wP98/uOffce8/5znfuPXvBYOWcx/es3zNe7aoqHR4Eg6UuFzEXzKmseGJeWenCihKQZ+AaHlw2WD5U9tNqi0+oBrcJWoauWdA9kFA1i0srg1jS1DidtxSL0/gEtDgkcvFw00aO9ADOMHWki7qKuaORIOaj/QEoCxTNEBRPgoCt1SbWbNWDGC0Rfh8vCQwtED6W9NnjlpWEUc1CvIaCGAlIgAMaJ/2twM8xNEcxHpYIdGLudmhaiq7ZJh6AhdLucum5Zp6vM7vKWxY0kb0IFoqGG+PN4WikIda62pu3ViiLQxzxKGlNlep1CbrbeTUJZ97GSltz8aQoQsvCvKHMDlMX5cITztyF+2moaUEEDCtDIiBQAUmWCwJlo24meDSzH45GkXA5bcpBDSkoNRuiNhrCDiiirBSzl4hG3M7Xk0leVWQFmkGsYW24I9zSgoWc3aHAp/AEb/ZAZKi8CHHR5lAyAU1F4gIAUrJP9OO0QDE4zZIBPCBDGpeBj6BZSPmBJGadyOyUTcE0L+p1TVIcQC13TEdroR0RnI4bnYebbdSsNZthGTne5uzYPHwB2+kkPJPhJNquOTmHCRskd1qcPTsTdJkkSKEIQ/GSz+fUup8QGZalAqLIF4I0oWzevDOljKYF2seLMk6RARGnJUbEWZaBOAl90EfyFA1k+n/eZBKNkKkISQRz3Jk+4G40FahJasrxMojFoWhChLmnSNPnpA+uLHUGrCC2HSGD83r7+/s9/ZRHN7u9JACEd0vTxri4HSZsZkzYKrMb40qaQCK0Z1kKh1KG7ciAzVF7c60bCxED8aZ1gkxH2ho37zDaY5s6opDWYrGmvhat09/Lsi2K2dQYMzsUKzhB/ymOh6ZrZ4NE1A3YoquKmMrhkqcqLnAs1MKbKFWQwC0nymzITq1nFUUVsDPfshfgDcXjlKtH1BNenbfPR0fVlfa4IGCEDSOaSCQRL6gwKk0QYZq2qKAhKJYhmYIEr9i9h3vyZ1GFaSc8k3mFl53zWVehx+QNpJs2FzRk6qoKTY9zYzrthcg7h219bqBg3JhCiSJjwn+8y5xan+k+uzeweMNQcmBlhKICq4BlE4F9k7zICEUV6j/yQoJ9NiV8gJZZUSRxlmKhzQuBwgUf48ehSNI+iSQIiQAFwUhUbQl1TVJiUlH8WN1dP3iPZzGfa8+cn/cIUvmQ62iB64cJ+Fk/DfyM3YkWJOL6NCNaU7lmJF9TVCRZr1sISv826GmKvBb9b//avFOfVEIl6Q8x5DoGhlxHSl0u4AWPEUvBo5VlbeVl9y+0FASdC9FjKd0aj5Im9PTAlMErZmmly1jz3p9P5z3iDG8FD+eeceaUEXPz3nTAosmRCqLmoWobEJr029mlKaYTLJ0cLSdqyx/ktrzc++K8nu+6rh7+9OLIB3QXPLUAVOeMXK6KEptxJYk3hm5cf6cM1R4/cnq8fuVy8mbNhoPeqqtvm8LHaPMzu1c88uqZwztf7xnvnj969qm6ZUTf7WMtK8CZhtvWiG+sTqr+Y8vokkNXvlDq5h84zS84cFO++v3X+43uxb3x08u1yN6le1dd2hcfWdU+vMvq/O3QyV13Xvq9Y+vo2Qc6gsm6E60XVt+4fFw7t7/m+YaqfV0/V7ZdVGtfGW1+a4Pwq3Ks9/1ICRPxfjWP/fbZK7cSn1+7+MPi85e3bRr55fAn66XRsRtLpJPvqhe+nDv25qFz111KbM2indtuvVBWszJQdfO+Ex3XrN0fPZf48ZtLp24dTdW2DaPOD3tfOzh+YZhac/6zJdy6U2O77mTS9xcGBzLWXhMAAA==
//-l -e=TEST -ct 0 --prev_file=/Users/snbanerjee/tmp/latest_4_29.gz --current_file=/Users/snbanerjee/tmp/latest_4_29.gz --out=/Users/snbanerjee/tmp/ --t=v^1.1#i^1#r^0#p^1#f^0#I^3#t^H4sIAAAAAAAAAO1Yb2wTZRin+2cWNv5EBbJoKIdAhlz73vXa3l1otayMNW7dWMfGFmB5e/fedtDeHXdvt5aILhMxIZooGoIY4ySKEQUVJPHPB4OCASWIH4iiBEmIkQ9ANILoWKJ37dZ202wK/dAP9kN7z/M+977P83t+z/s+fcFAReXS7Q3bb1bb7ioZGgADJTYbNR1UVpQ/OKO0pKZ8GsgzsA0NPDBQNlh6ebkB4zGNb0WGpioGsifjMcXg00ofkdAVXoWGbPAKjCODxwIfCTQ18rQD8JquYlVQY4Q9FPQRkGYZF+sVBK+H5gALTK0yNmeb6iM8SORYWmAhhIzgBcgcN4wECikGhgr2ETSgAQkYkubaaMADjncBB+ehugh7O9INWVVMEwcg/Gl3+fS7ep6vk7sKDQPp2JyE8IcC9ZHmQCi4Mty23Jk3l38UhwiGOGGMl+pUEdnbYSyBJl/GSFvzkYQgIMMgnP7MCuMn5QNjztyG+2mooyyIApbxuEwoWQlJBYGyXtXjEE/uh6WRRVJKm/JIwTJOTYWoiUZ0IxLwqBQ2pwgF7dbP6gSMyZKMdB+xckWgM9DSQvit1VEUpsg41DchrMWggEjB5FAijnRZ5FmAXJJH8JJM1OUmGY5mSRMBhpSAh2I45PICURh1IrPSaAomeFGnKqJsAWrYwypegcyI0DjcKI535+FmGjUrzXpAwpa3GTs3Cag8fN2mnXMswwncq1g5R3ETJHtanDo7Y3TJEaRQhKERK7FWrVN0VKRdwE2BQnDGP5o252QZY5go44GCRLpoViAZ0S2QHOdGJI08yENDFwMk5n/aZPKMsS5HExhlqTNxwF6vy0gRYynLSx8RQYKOMGEfJ018J71vjTInafiIXow13uns7+939Lscqt7jpAGgnGubGiNCL4pDImsrT21MymkCCeZ2btrzOKWZjiRNipqLKz2En0pGmlZFJSa4pr5jo9Yebu0MIUYJh5v6WpQu72aOa5H1pvqw3ikbvjH2j3PcP1E7FSSCqqEWNSYLqSwueariAsfALVDHqYIEblhRjoVs1XpGUVQBW+8b5gRQkx1WuToENe5Uobk9WqrutMcFASOgaaF4PIFhNIZC4hgqE7RFBQ3l4ty0uyDBy2brYc89FlWYZsIzmZehZO3Pagw5dKhhVTe5oGBdjcWQ7rAOTKu7EKC12dZlBwrGjXGUKDIm/NezzKr1Sc6zOwMLapqcBSsjFBVYBSybIOrL8SIjFFWo/8gLEfWZlPAARuIEgSY5F4dMXkRdZNTj9pJIoBmPSFOUSIGCYCTETAl35yiRUxQ/VrfXD97hXgyz7Zn1eKcglQ3aDhW2ftwsS3GA8wKzEy1IxHVpRrSlcs1InqaoSNKgGhiJ/zboCYq8Fv1vf9qc429U/NPSH2rQdgQM2t4rsdmAEyyiFoIFFaVrykqragwZI+tAdBhyjwJxQkeOTSilQVkvqbBpD789vC7vDmdoPZiXvcWpLKWm513pgPtyI+XUzLnVJiAMzZnfnAt0gYW50TJqTtk9nz1/rmv28acWhqukM98f37xO8XqDoDprZLOVTzMJN+2xY79B8Quw6suLs84sW5+oO3pyRtucrS8OV7U8Vxm84t/xzcjT53++tfLrJ+b90X1haMe5+e23tkjPEgdb9174+LvPX+v86NSSbupQeaDjk5MPxW+IG/5cPfJoTcPddUuPvXFi7o+7d+1a+/ur8vCvVYs73j96eL+9dtmbtbt9L42Ef5rpOLjtwAt7Fs3euu/M2cUfLr96tjdZc/TyL+dtVxcvWLIWfruhT3h6yyPVew/sqiWZS3srGn1v3R/advL0M3u+Oqxeovfz2k7fvZ+Kj7fWznq96YPmJM9cfMfe0TMcfGXW9XjX9UOn9u15NznYKONkw82l8598+UTk2ukflHm3rlzbdsNgjmi93M4NI5FM+v4CzJ4qY10TAAA=
@SpringBootApplication
@EnableAsync
public class Driver implements CommandLineRunner {
	@Autowired
	private DataComparator dataComparator;

	@Autowired
	private OptionsHandler optionsHandler;

	private Logger logger = LoggerFactory.getLogger(Driver.class);

	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args).close();
	}

	@Override
	public void run(String... args) {
		optionsHandler.handleArguments(args);
		dataComparator.compare();
	}



	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(100);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}

}
