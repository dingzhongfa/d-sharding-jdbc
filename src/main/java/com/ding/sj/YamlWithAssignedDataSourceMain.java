/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.ding.sj;

import com.dangdang.ddframe.rdb.sharding.config.yaml.api.YamlShardingDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class YamlWithAssignedDataSourceMain {

    // CHECKSTYLE:OFF
    public static void main(final String[] args) throws Exception {
        // CHECKSTYLE:ON
        YamlShardingDataSource dataSource = new YamlShardingDataSource(
                new File(YamlWithAssignedDataSourceMain.class.getResource("/META-INF/withAssignedDataSource.yaml").getFile()));
        insertByGeneratedKey(dataSource);
        printSimpleSelect(dataSource);
        printJoinSelect(dataSource);
        printGroupBy(dataSource);
        delete(dataSource);
    }

    private static void freeAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }


    }

    private static void insertByGeneratedKey(final DataSource dataSource) throws SQLException {

        String sql = "INSERT INTO t_order(order_id,user_id, status) values (1213123,1000, 'INSERT')";

        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();
        freeAll(conn, preparedStatement, null);

        sql = "SELECT * FROM t_order WHERE user_id = 1000";
        Connection conn2 = dataSource.getConnection();
        PreparedStatement preparedStatement2 = conn2.prepareStatement(sql);
        ResultSet rs = preparedStatement2.executeQuery();
        while (rs.next()) {
            System.out.print(rs.getLong(1) + "," + rs.getInt(2) + "," + rs.getString(3));
            System.out.println();
        }
        freeAll(conn2, preparedStatement2, rs);
    }


    private static void delete(final DataSource dataSource) throws SQLException {
        String sql = "DELETE FROM t_order where user_id = 1000";

        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();
        freeAll(conn, preparedStatement, null);

    }

    private static void printSimpleSelect(final DataSource dataSource) throws SQLException {
        String sql = "SELECT c.* FROM t_config c";

        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
        }
        freeAll(conn, preparedStatement, rs);
    }


    private static void printJoinSelect(final DataSource dataSource) throws SQLException {
        String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";

        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, 10);
        preparedStatement.setInt(2, 1001);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1));
            System.out.println(rs.getInt(2));
            System.out.println(rs.getString(3));
        }
        freeAll(conn, preparedStatement, rs);
    }


    private static void printGroupBy(final DataSource dataSource) throws SQLException {
        String sql = "SELECT o.user_id, COUNT(*) FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id GROUP BY o.user_id";

        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println("user_id: " + rs.getInt(1) + ", count: " + rs.getInt(2));
        }
        freeAll(conn, preparedStatement, rs);
    }
}

