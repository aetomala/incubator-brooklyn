/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package brooklyn.enricher.basic;

import java.util.Map;

import brooklyn.entity.rebind.BasicEnricherRebindSupport;
import brooklyn.entity.rebind.RebindSupport;
import brooklyn.mementos.EnricherMemento;
import brooklyn.policy.Enricher;
import brooklyn.policy.EnricherType;
import brooklyn.policy.basic.AbstractEntityAdjunct;

import com.google.common.collect.Maps;

/**
* Base {@link Enricher} implementation; all enrichers should extend this or its children
*/
public abstract class AbstractEnricher extends AbstractEntityAdjunct implements Enricher {

    private final EnricherDynamicType enricherType;

    public AbstractEnricher() {
        this(Maps.newLinkedHashMap());
    }
    
    public AbstractEnricher(Map<?,?> flags) {
        super(flags);
        
        enricherType = new EnricherDynamicType(this);
        
        if (isLegacyConstruction() && !isLegacyNoConstructionInit()) {
            init();
        }
    }

    @Override
    public RebindSupport<EnricherMemento> getRebindSupport() {
        return new BasicEnricherRebindSupport(this);
    }
    
    @Override
    public EnricherType getEnricherType() {
        return enricherType.getSnapshot();
    }

    @Override
    protected void onChanged() {
        requestPersist();
    }
    
}
