/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.impl.internal;

import org.ehcache.core.spi.time.SystemTimeSource;
import org.ehcache.core.spi.time.TimeSource;
import org.ehcache.core.spi.time.TimeSourceService;
import org.ehcache.core.spi.ServiceLocator;
import org.junit.Test;

import static org.ehcache.core.spi.ServiceLocator.dependencySet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;

/**
 * DefaultTimeSourceServiceTest
 */
public class DefaultTimeSourceServiceTest {

  @Test
  public void testResolvesDefaultTimeSource() {
    ServiceLocator.DependencySet dependencySet = dependencySet().with(TimeSourceService.class);
    ServiceLocator serviceLocator = dependencySet.build();
    assertThat(serviceLocator.getService(TimeSourceService.class).getTimeSource(),
        sameInstance(SystemTimeSource.INSTANCE));
  }

  @Test
  public void testCanConfigureAlternateTimeSource() {
    TimeSource timeSource = mock(TimeSource.class);
    ServiceLocator serviceLocator = dependencySet().with(new TimeSourceConfiguration(timeSource)).build();
    TimeSourceService timeSourceService = serviceLocator.getService(TimeSourceService.class);
    assertThat(timeSourceService.getTimeSource(), sameInstance(timeSource));
  }

}
