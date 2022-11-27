package com.example.data.mappers

import com.example.data.db.model.UserDb
import com.example.domain.model.BriefInfo
import com.example.domain.model.OtherInfo
import com.example.domain.model.User
import com.example.network.model.UserResponse

fun UserDb.toBriefInfo() = BriefInfo(
    id = id,
    name = name,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

fun User.toBriefInfo() = BriefInfo(
    id = id,
    name = name,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)

fun UserResponse.toOtherInfo() = OtherInfo(
    company = company.orEmpty(),
    location = location.orEmpty(),
    email = email.orEmpty(),
    bio = bio.orEmpty(),
    twitterUsername = twitterUsername.orEmpty(),
    followers = followers.toString(),
    following = following.toString(),
    createdAt = createdAt.orEmpty()

)

internal fun UserDb.toOtherInfo() = OtherInfo(
    company = company,
    location = location,
    email = email,
    bio = bio,
    twitterUsername = twitterUsername,
    followers = followers.toString(),
    following = following.toString(),
    createdAt = createdAt
)

fun UserResponse.toDb() = UserDb(
    login = login.orEmpty(),
    id = id,
    name = name.orEmpty(),
    nodeId = nodeId.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    gravatarId = gravatarId.orEmpty(),
    url = url.orEmpty(),
    htmlUrl = htmlUrl.orEmpty(),
    followersUrl = followersUrl.orEmpty(),
    followingUrl = followingUrl.orEmpty(),
    gistsUrl = gistsUrl.orEmpty(),
    starredUrl = starredUrl.orEmpty(),
    subscriptionsUrl = subscriptionsUrl.orEmpty(),
    organizationsUrl = organizationsUrl.orEmpty(),
    reposUrl = reposUrl.orEmpty(),
    eventsUrl = eventsUrl.orEmpty(),
    receivedEventsUrl = receivedEventsUrl.orEmpty(),
    type = type.orEmpty(),
    siteAdmin = siteAdmin ?: false,
    location = location.orEmpty(),
    company = company.orEmpty(),
    bio = bio.orEmpty(),
    createdAt = createdAt.orEmpty(),
    following = following ?: 0,
    followers = followers ?: 0,
    email = email.orEmpty(),
    twitterUsername = twitterUsername.orEmpty()
)

fun UserResponse.toUser() = User(
    login = login.orEmpty(),
    id = id,
    name = name.orEmpty(),
    nodeId = nodeId.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    gravatarId = gravatarId.orEmpty(),
    url = url.orEmpty(),
    htmlUrl = htmlUrl.orEmpty(),
    followersUrl = followersUrl.orEmpty(),
    followingUrl = followingUrl.orEmpty(),
    gistsUrl = gistsUrl.orEmpty(),
    starredUrl = starredUrl.orEmpty(),
    subscriptionsUrl = subscriptionsUrl.orEmpty(),
    organizationsUrl = organizationsUrl.orEmpty(),
    reposUrl = reposUrl.orEmpty(),
    eventsUrl = eventsUrl.orEmpty(),
    receivedEventsUrl = receivedEventsUrl.orEmpty(),
    type = type.orEmpty(),
    siteAdmin = siteAdmin ?: false,
    location = location.orEmpty(),
    company = company.orEmpty(),
    bio = bio.orEmpty(),
    createdAt = createdAt.orEmpty(),
    following = following ?: 0,
    followers = followers ?: 0,
    email = email.orEmpty(),
    twitterUsername = twitterUsername.orEmpty()
)

fun UserDb.toUser() = User(
    login = login,
    id = id,
    name = name,
    nodeId = nodeId,
    avatarUrl = avatarUrl,
    gravatarId = gravatarId,
    url = url,
    htmlUrl = htmlUrl,
    followersUrl = followersUrl,
    followingUrl = followingUrl,
    gistsUrl = gistsUrl,
    starredUrl = starredUrl,
    subscriptionsUrl = subscriptionsUrl,
    organizationsUrl = organizationsUrl,
    reposUrl = reposUrl,
    eventsUrl = eventsUrl,
    receivedEventsUrl = receivedEventsUrl,
    type = type,
    siteAdmin = siteAdmin,
    location = location,
    company = company,
    bio = bio,
    createdAt = createdAt,
    following = following,
    followers = followers,
    email = email,
    twitterUsername = twitterUsername
)
