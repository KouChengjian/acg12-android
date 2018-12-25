package org.acg12.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

public class Param implements Serializable{
	private static final long serialVersionUID = -7180816291844644837L;

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
	protected long _id;

	public long get_Id() {
		return _id;
	}

	public void set_Id(long id) {
		this._id = _id;
	}

}
