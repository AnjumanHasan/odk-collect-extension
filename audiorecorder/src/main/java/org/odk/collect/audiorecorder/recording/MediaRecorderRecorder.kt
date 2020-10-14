package org.odk.collect.audiorecorder.recording

import android.media.MediaRecorder
import java.io.File


internal class MediaRecorderRecorder(private val cacheDir: File, private val mediaRecorderFactory: () -> MediaRecorderWrapper) : Recorder {

    private var mediaRecorder: MediaRecorderWrapper? = null
    private var file: File? = null

    override fun start() {
        val tempFile = File.createTempFile("recording", ".m4a", cacheDir)
        file = tempFile

        mediaRecorder = mediaRecorderFactory().also {
            it.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            it.setAudioSource(MediaRecorder.AudioSource.MIC)
            it.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            it.setOutputFile(tempFile.absolutePath)
            it.prepare()
            it.start()
        }
    }

    override fun stop(): File {
        stopAndReleaseMediaRecorder()

        return file!!
    }

    override fun cancel() {
        stopAndReleaseMediaRecorder()
        file?.delete()
    }

    private fun stopAndReleaseMediaRecorder() {
        mediaRecorder?.apply {
            stop()
            release()
        }

        mediaRecorder = null
    }
}