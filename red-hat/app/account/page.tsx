"use client";

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'


export default function Page() {
    const [account, setAccount] = useState('');

    // const url = 'http://localhost:8080/Al-Akeel/api/users';
    // useEffect(() => {
    //     fetch(url)
    //         .then(res => res.json())
    //         .then(data => {
    //             setAccount(data);
    //         })
    // }, []);

    return (
        <main>
            <div className={styles.main}>
                <h1 className={styles.h1}>Account</h1>
                <br></br>
                <div className={styles.grid}>
                    {Object.keys(account).map((key) => (
                        <a
                            href="/account"
                            className={styles.card}
                            target="_self"
                            rel="noopener noreferrer"
                        >
                            <h2>
                                {/* {key}: {account[key]} <span></span> */}
                            </h2>
                        </a>
                    ))}
                </div>
            </div>
        </main>  
    )

}
