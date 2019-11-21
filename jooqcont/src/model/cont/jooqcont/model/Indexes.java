/*
 * This file is generated by jOOQ.
 */
package cont.jooqcont.model;


import cont.jooqcont.model.tables.DataModel;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>compdb</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index DATA_MODEL_PRIMARY = Indexes0.DATA_MODEL_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index DATA_MODEL_PRIMARY = Internal.createIndex("PRIMARY", DataModel.DATA_MODEL, new OrderField[] { DataModel.DATA_MODEL.ID }, true);
    }
}
