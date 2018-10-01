/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.processor;

import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.util.ServiceHelper;

/**
 * A processor which ensures wrapping processors is having lifecycle handled.
 */
public class WrapProcessor extends DelegateAsyncProcessor {
    private final Processor wrapped;

    public WrapProcessor(Processor processor, Processor wrapped) {
        super(processor);
        this.wrapped = wrapped;
    }

    @Override
    public String toString() {
        return "Wrap[" + wrapped + "] -> " + processor;
    }

    @Override
    public List<Processor> next() {
        // must include wrapped in navigate
        List<Processor> list = super.next();
        list.add(wrapped);
        return list;
    }

    @Override
    protected void doStart() throws Exception {
        ServiceHelper.startService(wrapped);
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        ServiceHelper.stopService(wrapped);
    }

}
