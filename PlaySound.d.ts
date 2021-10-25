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
    function playSoundStreamVolume(value: number, stream: AndroidStream): void
}
