package com.binartest.challenge8.worker


// Name of Notification Channel for verbose notifications of background work
@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the image manipulation work
@Suppress("unused")
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Other keys
const val OUTPUT_PATH = "blur_outputs"
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
@Suppress("unused")
const val TAG_OUTPUT = "OUTPUT"
@Suppress("unused")
const val DELAY_TIME_MILLIS: Long = 3000