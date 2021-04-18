/*
Copyright 2014 António Miranda, Caio Valente, Igor Topcin, Jorge Melegati, Thales Paiva, Victor Santos

This file is part of PhysUSP.

PhysUSP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PhysUSP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PhysUSP. If not, see <http://www.gnu.org/licenses/>.
*/

package com.gedaeusp.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

@Alternative
@Priority(Interceptor.Priority.APPLICATION)
public class UnitValueConverter implements JsonSerializer<UnitValue> {

    @Override
    public JsonElement serialize(UnitValue obj, Type type, JsonSerializationContext jsc) {
        JsonObject json = new JsonObject();
        UnitValue<?> unitValue = (UnitValue<?>) obj;
        Class<?> unitType = unitValue.getUnit().getClass();
        for(Object unitObj : unitType.getEnumConstants()) {
                Unit unit = (Unit)unitObj;
                json.addProperty(unit.toString(), Double.toString(unitValue.getValue(unitObj)));
        }
        return json;
    }


}
