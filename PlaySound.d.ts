export type AndroidStream
    ="SYSTEM"
    | "MUSIC"
    | "NOTIFICATION"
    | "ALARM"

export module PlaySound {
    function playSound(soundPath: string, stream :AndroidStream): void
    function stopSound(): void
    function playSoundRepeat(soundPath: string): void
    function playSoundMusicVolume(value: number): void

    /**
     * Android only. Set volume on the specified audio stream
     * @param value new volume level between 0 and 1
     * @param stream audio stream to use
     * @return the previously set volume
     */
    function setStreamVolume(value: number, stream: AndroidStream): number
}
