package org.acg12.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

public class Param implements Serializable{
	private static final long serialVersionUID = -7180816291844644837L;

	@PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
