package com.wugao.vankeda.infrastructure.ftp;


public interface ConnectionCallback {

	<T> T doInConnection(Connection connection);

}
