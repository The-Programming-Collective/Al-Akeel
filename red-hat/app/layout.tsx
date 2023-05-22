import './globals.css'
import { Inter } from 'next/font/google'

const inter = Inter({ subsets: ['latin'] })

export const metadata = {
  title: 'Al-Akeel',
  description: 'Al-Akeel homepage.',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <><link rel='manifest' href='/manifest.json' /><html lang="en">
      <body className={inter.className}>{children}</body>
    </html></>
  )
}
