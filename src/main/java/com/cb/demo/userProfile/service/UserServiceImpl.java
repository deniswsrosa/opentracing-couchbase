package com.cb.demo.userProfile.service;

import com.cb.demo.userProfile.model.UserEntity;
import com.cb.demo.userProfile.service.vo.SimpleUserVO;
import com.cb.demo.userProfile.service.vo.UserVO;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.*;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.BooleanFieldQuery;
import com.couchbase.client.java.search.queries.ConjunctionQuery;
import com.couchbase.client.java.search.queries.DisjunctionQuery;
import com.couchbase.client.java.search.queries.MatchQuery;
import com.couchbase.client.java.search.result.SearchQueryResult;
import com.couchbase.client.java.search.result.SearchQueryRow;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private Bucket bucket;

    @Override
    public UserEntity save(UserEntity user) {
        if(isEmpty(user.getId())) {
            user.setId("User::"+UUID.randomUUID().toString());
        }

        bucket.upsert(RawJsonDocument.create(user.getId(), toJSon(user)));
        return user;
    }

    @Override
    public UserVO getUser(String id) {
        RawJsonDocument doc = RawJsonDocument.create(id);
        RawJsonDocument raw = bucket.get(doc);
        Gson gson = new Gson();
        UserVO vo = gson.fromJson(raw.content(), UserVO.class);
        return vo;
    }



//    @Override
//    public List<SimpleUserVO> listUsers(Integer tenantId, Integer offset, Integer limit) {
//        return null;
//    }
//
    @Override
    public List<SimpleUserVO> ftsListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip ) {

        String indexName = "user_index";
        MatchQuery firstNameFuzzy = SearchQuery.match(firstName).fuzziness(1).field("firstName");
        MatchQuery firstNameSimple = SearchQuery.match(firstName).field("firstName");
        DisjunctionQuery nameQuery = SearchQuery.disjuncts(firstNameSimple, firstNameFuzzy);

        BooleanFieldQuery isEnabled = SearchQuery.booleanField(enabled).field("enabled");
        MatchQuery countryFilter = SearchQuery.match(countryCode).field("countryCode");

        ConjunctionQuery conj = SearchQuery.conjuncts(nameQuery, isEnabled, countryFilter);

        SearchQueryResult result = bucket.query(
                new SearchQuery(indexName, conj)
                        .fields("id", "tenantId", "firstName", "lastName", "username" )
                        .skip(skip)
                        .limit(limit));


        List<SimpleUserVO> simpleUsers = new ArrayList<>();
        if (result != null && result.errors().isEmpty()) {
            Iterator<SearchQueryRow> resultIterator = result.iterator();
            while (resultIterator.hasNext()) {
                SearchQueryRow row = resultIterator.next();
                Map<String, String> fields = row.fields();
                simpleUsers.add(new SimpleUserVO(
                        row.id(),
                        new Long(fields.get("tenantId")),
                        fields.get("firstName"),
                        fields.get("lastName"),
                        fields.get("username")
                ));
            }
        }

        return simpleUsers;
    }

    @Override
    public List<SimpleUserVO> listActiveUsers( boolean enabled, String countryCode,  Integer limit, Integer offset ) {
        String query = "Select meta().id as id, username, tenantId, firstName, lastname from "
                + bucket.bucketManager().info().name()
                +" where type = '"+UserEntity.TYPE+"' and enabled = "+enabled+" " +
                " and countryCode = '"+countryCode+"' order by firstName desc limit "+limit+ " offset "+offset;

        N1qlParams params = N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS).adhoc(false);
        ParameterizedN1qlQuery queryParam = N1qlQuery.parameterized(query, JsonObject.create(), params);
        N1qlQueryResult result = bucket.query(queryParam);

        return result.allRows().stream()
                .map(e-> toSimpleUserVo(e))
                .collect(toList());
    }

    private SimpleUserVO toSimpleUserVo(N1qlQueryRow row) {
        JsonObject d = row.value();
        return new SimpleUserVO(d.getString("id"),
                d.getLong("tenantid"),
                d.getString("firstName"),
                d.getString("lastName"),
                d.getString("username"));
    }

//    private UserVO toUserVO(UserEntity user) {
//        UserVO userVO = new UserVO();
//        userVO.setId(user.getId());
//        userVO.setCountryCode(user.getCountryCode());
//        userVO.setEnabled(user.isEnabled());
//        userVO.setFirstName(user.getFirstName());
//        userVO.setLastName(user.getLastName());
//        userVO.setTenantId(user.getTenantId());
//        userVO.setUsername(user.getUsername());
//        userVO.setAddresses(user.getAddresses());
//        userVO.setPreferences(user.getPreferences());
//        userVO.setSecurityRoles(user.getSecurityRoles());
//        return userVO;
//    }


     private String toJSon(Object src) {
         Gson gson = new Gson();
        return gson.toJson(src);
     }


}
