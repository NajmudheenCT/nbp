// Copyright 2019 The OpenSDS Authors.
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may
// not use this file except in compliance with the License. You may obtain
// a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// License for the specific language governing permissions and limitations
// under the License.
package org.opensds.vmware.ngc.dao;

import org.opensds.vmware.ngc.common.Storage;
import org.opensds.vmware.ngc.model.DeviceInfo;


import java.util.Map;

public interface DeviceRepository {


    Map<String, DeviceInfo> getAll();

    void remove(String uid);

    void update(String uid, DeviceInfo device) throws Exception;

    void add(String uid , DeviceInfo device) throws Exception;

    DeviceInfo get(String uid);

    Storage getLoginedDeviceByIP(String deviceIP);


}
