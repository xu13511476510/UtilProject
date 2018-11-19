package com.binqing.utilproject.data.entry;

import com.binqing.utilproject.data.annotation.Column;
import com.binqing.utilproject.data.annotation.Table;
import com.binqing.utilproject.data.entry.interfaceEntry.AbsEntry;
import com.binqing.utilproject.data.object.TestObject;

@Table(name = "test1")
public class EntryTest implements AbsEntry {
    @Column(name = "t_name", order = 1)
    public String name;

    @Column(name = "t_id", order = 0)
    public long id;

    @Column(name = "t_testC", order = 2)
    public String testC;

    @Override
    public void fromObject(Object object) {
        if (object instanceof TestObject) {
            id = ((TestObject) object).getId();
            name = ((TestObject) object).getName();
            testC = ((TestObject) object).getTestC();
        }
    }

    @Override
    public TestObject fromEntry() {
        TestObject object = new TestObject();
        object.setId(id);
        object.setName(name);
        object.setTestC(testC);
        return object;
    }
}
