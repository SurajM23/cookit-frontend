package com.example.cookit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cookit.model.ApiResult
import com.example.cookit.model.UserProfile
import com.example.cookit.ui.composables.CookitActionButton
import com.example.cookit.ui.theme.PrimaryColor
import com.example.cookit.ui.theme.SecondaryColor
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.User

@Composable
fun ProfileHeaderSection(
    profileState: ApiResult<UserProfile>,
    postCount: Int,
    isCurrentUser: Boolean,
    action: String,
    onActionClick: () -> Unit
) {
    when (profileState) {
        is ApiResult.Loading -> {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ApiResult.Error -> {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                Text(profileState.message)
            }
        }

        is ApiResult.Success -> {
            val profile = profileState.data
            ProfileHeader(
                profile = profile,
                postCount = postCount,
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProfileHeader(
    profile: UserProfile,
    postCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarView(profile.avatarUrl)

        Spacer(Modifier.height(10.dp))
        Text(profile.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
        Text("@${profile.username}", fontSize = 15.sp, color = Color.Black)

        Spacer(Modifier.height(8.dp))
        StatsRow(postCount, profile.followersCount, profile.followingCount)

        if (profile.bio.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                profile.bio,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
fun AvatarView(url: String) {
    Box(
        modifier = Modifier
            .size(92.dp)
            .clip(CircleShape)
            .border(
                2.dp,
                Brush.linearGradient(
                    listOf(
                        PrimaryColor,
                        SecondaryColor
                    )
                ),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (url.isEmpty()) {
            Icon(
                FontAwesomeIcons.Regular.User,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .padding(20.dp)
            )
        } else {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun StatsRow(posts: Int, followers: Int, following: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        ProfileCountItem(posts, "Posts")
        ProfileCountItem(followers, "Followers")
        ProfileCountItem(following, "Following")
    }
}
